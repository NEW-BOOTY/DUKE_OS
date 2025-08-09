import os

def extract_java_files(bundle_path, output_dir):
    os.makedirs(output_dir, exist_ok=True)
    with open(bundle_path, 'r') as bundle:
        content = bundle.read()
    programs = content.split("---------")  # Assuming dashes as the delimiter
    for i, program in enumerate(programs):
        if program.strip():  # Skip empty segments
            file_path = os.path.join(output_dir, f"Program{i + 1}.java")
            with open(file_path, 'w') as java_file:
                java_file.write(program.strip())

# Example usage
extract_java_files('my_programs.java_bundle', 'output_java_files')
